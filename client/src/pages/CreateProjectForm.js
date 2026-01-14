import { useState, useEffect, useRef } from 'react';
import { createProject, getQualifications } from '../services/dataService';
import { 
    grayContainerStyle, 
    addWorkerButtonStyle,
    submitButtonStyle,
    formLabelStyle,
    formInputStyle,
    formGroupStyle,
    formHeadingStyle,
    errorMessageStyle,
    successMessageStyle
} from '../utils/styles';

const eventSystem = {
    events: {},
    subscribe(event, callback) {
        if (!this.events[event]) {
            this.events[event] = [];
        }
        this.events[event].push(callback);
        return () => {
            this.events[event] = this.events[event].filter(cb => cb !== callback);
        };
    },
    publish(event, data) {
        if (this.events[event]) {
            this.events[event].forEach(callback => callback(data));
        }
    }
};

export const qualificationEvents = eventSystem;

const CreateProjectForm = ({ onProjectCreated }) => {
    const [name, setName] = useState('');
    const [size, setSize] = useState('SMALL');
    const [selectedQualifications, setSelectedQualifications] = useState([]);
    const [availableQualifications, setAvailableQualifications] = useState([]);
    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [loading, setLoading] = useState(false);
    const [showForm, setShowForm] = useState(false);
    const scrollPositionRef = useRef(0);

    useEffect(() => {
        if (showForm) {
            loadQualifications();
            const unsubscribe = qualificationEvents.subscribe('qualificationCreated', () => {
                scrollPositionRef.current = window.scrollY;
                loadQualifications();
            });
            return () => unsubscribe();
        }
    }, [showForm]);

    useEffect(() => {
        if (scrollPositionRef.current > 0) {
            window.scrollTo(0, scrollPositionRef.current);
        }
    }, [availableQualifications]);

    const loadQualifications = () => {
        setLoading(true);
        getQualifications()
            .then(qualifications => {
                setAvailableQualifications(qualifications);
                setLoading(false);
            })
            .catch(err => {
                console.error('Error loading qualifications:', err);
                setErrorMessage('Failed to load qualifications. Please try again.');
                setLoading(false);
            });
    };

    const handleToggleForm = () => {
        setShowForm(!showForm);
        if (!showForm) resetForm();
    };

    const resetForm = () => {
        setName('');
        setSize('SMALL');
        setSelectedQualifications([]);
        setErrorMessage('');
        setSuccessMessage('');
    };

    const handleQualificationToggle = (qualification) => {
        if (selectedQualifications.includes(qualification.description)) {
            setSelectedQualifications(
                selectedQualifications.filter(q => q !== qualification.description)
            );
        } else {
            setSelectedQualifications([...selectedQualifications, qualification.description]);
        }
    };

    const validateForm = () => {
        if (!name.trim()) {
            setErrorMessage('Project name is required');
            return false;
        }
        if (selectedQualifications.length === 0) {
            setErrorMessage('At least one qualification is required');
            return false;
        }
        return true;
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setErrorMessage('');
        setSuccessMessage('');

        if (!validateForm()) return;

        setLoading(true);
        const projectData = {
            name: name.trim(),
            size,
            qualifications: selectedQualifications,
            status: 'PLANNED'
        };

        createProject(projectData)
            .then(response => {
                setSuccessMessage(`Project "${name}" created successfully!`);
                resetForm();
                setLoading(false);
                onProjectCreated?.();
                setTimeout(() => {
                    setShowForm(false);
                    setSuccessMessage('');
                }, 3000);
            })
            .catch(err => {
                console.error('Error creating project:', err);
                setErrorMessage('Failed to create project. Please try again.');
                setLoading(false);
            });
    };

    const qualificationsContainerStyle = {
        maxHeight: '150px',
        overflowY: 'auto',
        border: '1px solid #ccc',
        padding: '10px',
        borderRadius: '4px'
    };

    return (
        <>
            <button 
                onClick={handleToggleForm}
                style={{ ...addWorkerButtonStyle, backgroundColor: '#FFC038', color: 'black' }}
            >
                {showForm ? 'Cancel' : 'Add New Project'}
            </button>

            {showForm && (
                <div style={grayContainerStyle}>
                    <h2 style={formHeadingStyle}>Create New Project</h2>

                    {errorMessage && <div style={errorMessageStyle}>{errorMessage}</div>}
                    {successMessage && <div style={successMessageStyle}>{successMessage}</div>}

                    <form onSubmit={handleSubmit}>
                        <div style={formGroupStyle}>
                            <label style={formLabelStyle}>
                                Project Name:
                                <input
                                    type="text"
                                    value={name}
                                    onChange={(e) => setName(e.target.value)}
                                    style={formInputStyle}
                                />
                            </label>
                        </div>

                        <div style={formGroupStyle}>
                            <label style={formLabelStyle}>
                                Project Size:
                                <select
                                    value={size}
                                    onChange={(e) => setSize(e.target.value)}
                                    style={formInputStyle}
                                >
                                    <option value="SMALL">SMALL</option>
                                    <option value="MEDIUM">MEDIUM</option>
                                    <option value="BIG">BIG</option>
                                </select>
                            </label>
                        </div>

                        <div style={formGroupStyle}>
                            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '5px' }}>
                                <label style={formLabelStyle}>Required Qualifications:</label>
                            </div>
                            {loading ? (
                                <p>Loading qualifications...</p>
                            ) : (
                                <div style={qualificationsContainerStyle}>
                                    {availableQualifications.length === 0 ? (
                                        <p>No qualifications available. Please create qualifications first.</p>
                                    ) : (
                                        availableQualifications.map((qual) => (
                                            <div key={qual.description} style={{ marginBottom: '5px' }}>
                                                <label style={{ color: '#FFFFFF', display: 'flex', alignItems: 'center' }}>
                                                    <input
                                                        type="checkbox"
                                                        checked={selectedQualifications.includes(qual.description)}
                                                        onChange={() => handleQualificationToggle(qual)}
                                                        style={{ marginRight: '8px' }}
                                                    />
                                                    {qual.description}
                                                </label>
                                            </div>
                                        ))
                                    )}
                                </div>
                            )}
                        </div>

                        <button
                            type="submit"
                            disabled={loading}
                            style={{ ...submitButtonStyle, backgroundColor: '#FFC038', color: 'black' }}
                        >
                            {loading ? 'Creating...' : 'Create Project'}
                        </button>
                    </form>
                </div>
            )}
        </>
    );
};

export default CreateProjectForm;
