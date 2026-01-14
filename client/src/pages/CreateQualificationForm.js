import { useState } from 'react';
import { createQualification } from '../services/dataService';
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
import { qualificationEvents } from './CreateProjectForm';

const CreateQualificationForm = ({ onQualificationCreated }) => {
  const [description, setDescription] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const [showForm, setShowForm] = useState(false);

  const handleToggleForm = () => {
    setShowForm(!showForm);
    if (!showForm) resetForm();
  };

  const resetForm = () => {
    setDescription('');
    setErrorMessage('');
    setSuccessMessage('');
  };

  const validateForm = () => {
    if (!description.trim()) {
      setErrorMessage('Qualification description is required');
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

    createQualification(description.trim())
      .then((response) => {
        setSuccessMessage(`Qualification "${description}" created successfully!`);
        qualificationEvents.publish('qualificationCreated', description);
        resetForm();
        setLoading(false);

        onQualificationCreated?.();

        setTimeout(() => {
          setShowForm(false);
          setSuccessMessage('');
        }, 3000);
      })
      .catch((err) => {
        console.error('Error creating qualification:', err);
        setErrorMessage('Failed to create qualification. Please try again.');
        setLoading(false);
      });
  };

  return (
    <>
      <button
        onClick={handleToggleForm}
        style={{ ...addWorkerButtonStyle, backgroundColor: '#FFC038', color: 'black' }}
      >
        {showForm ? 'Cancel' : 'Add New Qualification'}
      </button>

      {showForm && (
        <div style={grayContainerStyle}>
          <h2 style={formHeadingStyle}>Create New Qualification</h2>

          {errorMessage && <div style={errorMessageStyle}>{errorMessage}</div>}
          {successMessage && <div style={successMessageStyle}>{successMessage}</div>}

          <form onSubmit={handleSubmit}>
            <div style={formGroupStyle}>
              <label style={formLabelStyle}>
                Qualification Description:
                <input
                  type="text"
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                  placeholder="e.g. Java, Python, React"
                  style={formInputStyle}
                />
              </label>
            </div>

            <button
              type="submit"
              disabled={loading}
              style={{ ...submitButtonStyle, backgroundColor: '#FFC038', color: 'black' }}
            >
              {loading ? 'Creating...' : 'Create Qualification'}
            </button>
          </form>
        </div>
      )}
    </>
  );
};

export default CreateQualificationForm;
