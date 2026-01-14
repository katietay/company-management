import { useState, useEffect } from 'react';
import { createWorker, getQualifications } from '../services/dataService';
import {
  addWorkerButtonStyle,
  formLabelStyle,
  formInputStyle,
  formGroupStyle,
  submitButtonStyle,
  errorMessageStyle,
  successMessageStyle,
  formHeadingStyle
} from '../utils/styles';

const CreateWorkerForm = ({ onWorkerCreated }) => {
  const [name, setName] = useState('');
  const [salary, setSalary] = useState('');
  const [selectedQualifications, setSelectedQualifications] = useState([]);
  const [availableQualifications, setAvailableQualifications] = useState([]);
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const [showForm, setShowForm] = useState(false);

  useEffect(() => {
    if (showForm) {
      setLoading(true);
      getQualifications()
        .then((qualifications) => {
          setAvailableQualifications(qualifications);
          setLoading(false);
        })
        .catch((err) => {
          console.error('Error loading qualifications:', err);
          setErrorMessage('Failed to load qualifications. Please try again.');
          setLoading(false);
        });
    }
  }, [showForm]);

  const handleToggleForm = () => {
    setShowForm(!showForm);
    if (!showForm) resetForm();
  };

  const resetForm = () => {
    setName('');
    setSalary('');
    setSelectedQualifications([]);
    setErrorMessage('');
    setSuccessMessage('');
  };

  const handleQualificationToggle = (qualification) => {
    if (selectedQualifications.includes(qualification.description)) {
      setSelectedQualifications(
        selectedQualifications.filter((q) => q !== qualification.description)
      );
    } else {
      setSelectedQualifications([...selectedQualifications, qualification.description]);
    }
  };

  const validateForm = () => {
    if (!name.trim()) {
      setErrorMessage('Name is required');
      return false;
    }

    const salaryNum = parseFloat(salary);
    if (isNaN(salaryNum) || salaryNum <= 0) {
      setErrorMessage('Salary must be a positive number');
      return false;
    }

    if (selectedQualifications.length === 0) {
      setErrorMessage('A worker must have at least one qualification to be employed in the company.');
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

    const workerData = {
      name: name.trim(),
      salary: parseFloat(salary),
      qualifications: selectedQualifications
    };

    createWorker(workerData)
      .then((response) => {
        setSuccessMessage(`Worker ${name} created successfully!`);
        resetForm();
        setLoading(false);

        onWorkerCreated?.();

        setTimeout(() => {
          setShowForm(false);
          setSuccessMessage('');
        }, 3000);
      })
      .catch((err) => {
        console.error('Error creating worker:', err);
        setErrorMessage('Failed to create worker. Please try again.');
        setLoading(false);
      });
  };

  return (
    <>
      <button onClick={handleToggleForm} style={addWorkerButtonStyle}>
        {showForm ? 'Cancel' : 'Add New Worker'}
      </button>

      {showForm && (
        <div style={{ backgroundColor: '#1E4D2B', padding: '20px', borderRadius: '8px', marginTop: '15px' }}>
          <h2 style={formHeadingStyle}>Create New Worker</h2>

          {errorMessage && <div style={errorMessageStyle}>{errorMessage}</div>}
          {successMessage && <div style={successMessageStyle}>{successMessage}</div>}

          <form onSubmit={handleSubmit}>
            <div style={formGroupStyle}>
              <label style={formLabelStyle}>
                Name:
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
                Salary:
                <input
                  type="number"
                  value={salary}
                  onChange={(e) => setSalary(e.target.value)}
                  style={formInputStyle}
                />
              </label>
            </div>

            <div style={formGroupStyle}>
              <label style={formLabelStyle}>Qualifications:</label>
              {loading ? (
                <p>Loading qualifications...</p>
              ) : (
                <div
                  style={{
                    maxHeight: '150px',
                    overflowY: 'auto',
                    border: '1px solid #008FB3',
                    padding: '10px',
                    borderRadius: '4px',
                    backgroundColor: 'rgba(0,0,0,0.2)'
                  }}
                >
                  {availableQualifications.length === 0 ? (
                    <p>No qualifications available.</p>
                  ) : (
                    availableQualifications.map((qual) => (
                      <div key={qual.description} style={{ marginBottom: '10px' }}>
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

            <button type="submit" disabled={loading} style={submitButtonStyle}>
              {loading ? 'Creating...' : 'Create Worker'}
            </button>
          </form>
        </div>
      )}
    </>
  );
};

export default CreateWorkerForm;
