import { useEffect, useState } from 'react'
import ClickList from '../components/ClickList'
import { getWorkers, getWorker } from '../services/dataService'
import LocationID from '../utils/location'
import CreateWorkerForm from './CreateWorkerForm'
import { 
  darkGrayContainerStyle, 
  grayContainerStyle, 
  pageStyle,
  blueContainerStyle,
  greenContainerStyle
} from '../utils/styles'

const Worker = (worker, active) => {
  return (
    <div>
      <div>{worker.name}</div>
      {active === true ? <WorkerBody worker={worker} /> : null}
    </div>
  )
}

const WorkerBody = ({ worker }) => {
  const [workerDetails, setWorkerDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchWorkerDetails = async () => {
      try {
        // Fetch the complete worker details with all required information
        const details = await getWorker(worker.name);
        setWorkerDetails(details);
        setLoading(false);
      } catch (err) {
        console.error("Error loading worker details:", err);
        setError("Cannot load worker details. Please try again later.");
        setLoading(false);
      }
    };

    fetchWorkerDetails();
  }, [worker.name]);

  if (loading) {
    return <div>Loading worker details...</div>;
  }

  if (error) {
    return <div style={{ color: 'red' }}>{error}</div>;
  }

  if (!workerDetails) {
    return <div>No details available for this worker.</div>;
  }

  return (
    <div style={grayContainerStyle}>
      <div style={{ padding: '15px', background: 'rgba(0,0,0,0.2)', borderRadius: '5px', marginBottom: '15px' }}>
        <h2 style={{ color: '#FFC038', marginBottom: '10px' }}>{workerDetails.name}</h2>
        
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '30px' }}>
          {/* Basic Worker Information */}
          <div style={{ flexBasis: '300px', flexGrow: 1 }}>
            <h3>Basic Information</h3>
            <div style={{ marginLeft: '15px' }}>
              <p><strong>Salary:</strong> ${workerDetails.salary}</p>
              <p><strong>Current Workload:</strong> {workerDetails.workload} units</p>
            </div>
          </div>
          
          {/* Worker Qualifications */}
          <div style={{ flexBasis: '300px', flexGrow: 1 }}>
            <h3>Qualifications</h3>
            {workerDetails.qualifications && workerDetails.qualifications.length > 0 ? (
              <div style={{ marginLeft: '15px' }}>
                <ul style={{ 
                  listStyleType: 'none',
                  padding: 0,
                  margin: 0,
                  display: 'flex',
                  flexWrap: 'wrap',
                  gap: '8px'
                }}>
                  {workerDetails.qualifications.map(qual => (
                    <li 
                      key={qual}
                      style={{
                        backgroundColor: '#4CAF50',
                        color: 'white',
                        padding: '5px 10px',
                        borderRadius: '4px',
                        cursor: 'pointer'
                      }}
                      onClick={() => window.location.assign(`/qualifications/${qual}`)}
                    >
                      {qual}
                    </li>
                  ))}
                </ul>
              </div>
            ) : (
              <p style={{ marginLeft: '15px' }}>No qualifications</p>
            )}
          </div>
        </div>
        
        {/* Projects Section */}
        <div style={{ marginTop: '20px' }}>
          <h3>Assigned Projects</h3>
          {workerDetails.projects && workerDetails.projects.length > 0 ? (
            <div style={{ marginLeft: '15px' }}>
              <ul style={{
                listStyleType: 'none',
                padding: 0,
                margin: 0
              }}>
                {workerDetails.projects.map(project => (
                  <li 
                    key={project}
                    style={{
                      backgroundColor: '#008FB3',
                      color: 'white',
                      padding: '8px 15px',
                      marginBottom: '10px',
                      borderRadius: '4px',
                      cursor: 'pointer'
                    }}
                    onClick={() => window.location.assign(`/projects/${project}`)}
                  >
                    {project}
                  </li>
                ))}
              </ul>
            </div>
          ) : (
            <p style={{ marginLeft: '15px' }}>Not assigned to any projects</p>
          )}
        </div>
      </div>
    </div>
  );
};

const Workers = () => {
  const [workers, setWorkers] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  const loadWorkers = () => {
    setLoading(true);
    getWorkers()
      .then(data => {
        setWorkers(data);
        setLoading(false);
      })
      .catch(err => {
        console.error('Error loading workers:', err);
        setError("Cannot load workers. Please try again later.");
        setLoading(false);
      });
  };

  useEffect(() => {
    loadWorkers();
  }, []);

  const handleWorkerCreated = () => {
    loadWorkers();
  };

  const active = LocationID('workers', workers, 'name');

  return (
    <div style={pageStyle}>
      <h1>
        This page displays a table containing all the workers.
      </h1>
      
      <CreateWorkerForm onWorkerCreated={handleWorkerCreated} />
      
      {loading && <p>Loading Workers</p>}
      {error && !loading && <p>{error}</p>}
      {workers.length === 0 && !loading && !error && (
        <p>There are no workers!</p>
      )}
      {!loading && !error && workers.length > 0 && (
        <ClickList active={active} list={workers} item={Worker} path='/workers' id='name' />
      )}
    </div>
  );
};

export default Workers