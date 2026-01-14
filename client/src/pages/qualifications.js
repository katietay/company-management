import { useEffect, useState } from 'react'
import ClickList from '../components/ClickList'
import { getQualifications } from '../services/dataService'
import LocationID from '../utils/location'
import CreateQualificationForm from './CreateQualificationForm'
import {
  darkGrayContainerStyle,
  grayContainerStyle,
  pageStyle,
  qualificationWorkerListStyle,
  qualificationWorkerHeadingStyle,
  noWorkersMessageStyle
} from '../utils/styles'

const Qualification = (qualification, active) => {
  return (
    <div>
      <div>{qualification.description}</div>
      {active === true ? QualificationBody(qualification) : null}
    </div>
  )
}

const QualificationBody = (qualification) => {
  return (
    <div style={grayContainerStyle}>
      Description: {qualification.description} <br/>
      <div style={qualificationWorkerListStyle}>
        <h3 style={qualificationWorkerHeadingStyle}>
          Workers with this qualification ({qualification.workers.length}):
        </h3>
        {qualification.workers.length > 0 ? (
          <ClickList
            list={qualification.workers}
            styles={darkGrayContainerStyle}
            path="/workers"
          />
        ) : (
          <p style={noWorkersMessageStyle}>
            No workers currently have this qualification.
          </p>
        )}
      </div>
    </div>
  )
}

const Qualifications = () => {
  const [qualifications, setQualifications] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  const loadQualifications = () => {
    setLoading(true);
    getQualifications()
      .then(data => {
        setQualifications(data);
        setLoading(false);
      })
      .catch(err => {
        console.error('Error loading qualifications:', err);
        setError("Cannot load qualifications. Please try again later.");
        setLoading(false);
      });
  };

  useEffect(() => {
    loadQualifications();
  }, []);

  const handleQualificationCreated = () => {
    loadQualifications();
  };

  const active = LocationID('qualifications', qualifications, 'description');

  return (
    <div style={pageStyle}>
      <h1>
        This page displays a table containing all the qualifications.
      </h1>
      
      <CreateQualificationForm onQualificationCreated={handleQualificationCreated} />
      
      {loading && <p>Loading Qualifications</p>}
      {error && !loading && <p>{error}</p>}
      {qualifications.length === 0 && !loading && !error && (
        <p>There are no qualifications!</p>
      )}
      {!loading && !error && qualifications.length > 0 && (
        <ClickList active={active} list={qualifications} item={Qualification} path='/qualifications' id='description' />
      )}
    </div>
  );
};

export default Qualifications