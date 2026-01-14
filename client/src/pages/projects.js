import { useEffect, useState } from 'react'
import ClickList from '../components/ClickList'
import { getProjects, startProject, finishProject, unassignWorker, getWorkers, assignWorker, getWorker } from '../services/dataService';
import LocationID from '../utils/location'
import CreateProjectForm from './CreateProjectForm'
import { 
  redContainerStyle, 
  greenContainerStyle, 
  darkGrayContainerStyle, 
  grayContainerStyle, 
  purpleContainerStyle, 
  yellowContainerStyle, 
  pageStyle,
  dropdownContainerStyle,
  dropdownStyle,
  dropdownItemStyle,
  overlayStyle,
  matchingQualStyle,
  missingQualStyle,
  modalOverlayStyle,
  modalContentStyle,
  modalCloseButtonStyle,
  workerHeadingStyle,
  workerDetailsSectionStyle,
  workerDetailsPanelStyle,
  workerInfoListStyle,
  qualBadgeStyle,
  qualBadgeListStyle,
  projectListItemStyle,
  viewDetailsButtonStyle,
  workerListItemStyle,
  workerNameLinkStyle,
  buttonGroupStyle
} from '../utils/styles'

const WorkerDetailsModal = ({ worker, onClose }) => {
  if (!worker) return null;
  
  return (
    <div style={modalOverlayStyle}>
      <div style={modalContentStyle}>
        <button 
          onClick={onClose}
          style={modalCloseButtonStyle}
        >
          ✕
        </button>

        <h2 style={workerHeadingStyle}>{worker.name}</h2>
        
        <div style={workerDetailsSectionStyle}>
          <div style={workerDetailsPanelStyle}>
            <h3>Basic Information</h3>
            <div style={workerInfoListStyle}>
              <p><strong>Salary:</strong> ${worker.salary}</p>
              <p><strong>Current Workload:</strong> {worker.workload} units</p>
            </div>
          </div>
          
          <div style={workerDetailsPanelStyle}>
            <h3>Qualifications</h3>
            {worker.qualifications && worker.qualifications.length > 0 ? (
              <div style={workerInfoListStyle}>
                <ul style={qualBadgeListStyle}>
                  {worker.qualifications.map(qual => (
                    <li 
                      key={qual}
                      style={qualBadgeStyle}
                      onClick={() => window.location.assign(`/qualifications/${qual}`)}
                    >
                      {qual}
                    </li>
                  ))}
                </ul>
              </div>
            ) : (
              <p style={workerInfoListStyle}>No qualifications</p>
            )}
          </div>
        </div>
        
        <div style={{ marginTop: '20px' }}>
          <h3>Assigned Projects</h3>
          {worker.projects && worker.projects.length > 0 ? (
            <div style={workerInfoListStyle}>
              <ul style={{
                listStyleType: 'none',
                padding: 0,
                margin: 0
              }}>
                {worker.projects.map(project => (
                  <li 
                    key={project}
                    style={projectListItemStyle}
                    onClick={() => window.location.assign(`/projects/${project}`)}
                  >
                    {project}
                  </li>
                ))}
              </ul>
            </div>
          ) : (
            <p style={workerInfoListStyle}>Not assigned to any projects</p>
          )}
        </div>
      </div>
    </div>
  );
};

const Project = (project, active) => {
    return (
        <div>
            <div>{project.name}</div>
            {active === true ? <ProjectBody project={project} /> : null}
        </div>
    )
}

const StartProjectButton = (project) => {
    let missingQuals = false;
    if(project.missingQualifications.length > 0) {
        missingQuals = true;
    }

    let startProjectBool = false;
    if(project.status === "PLANNED" || project.status === "SUSPENDED") {
        if(missingQuals === false) {
            startProjectBool = true;
        }
    }
    if(startProjectBool) {
        return (
                <div style={greenContainerStyle} onClick={async (e) => {
                    e.stopPropagation();
                    e.preventDefault();
                    
                    try {
                        await startProject(project);
                        console.log("Project started successfully");
                        window.location.reload();
                    } catch (err) {
                        console.error("Error starting project:", err);
                        alert("Error starting project: " + err.message);
                    }
                }}>
                    --- Start Project --- 
                </div>
        )
    } else {
        return (
            <div style={grayContainerStyle} onClick={(e) => {
                e.stopPropagation();
            }}>
            </div>
        )
    }
}

const FinishProjectButton = (project) => {
    let isActive = false;
    if(project.status === "ACTIVE") {
        isActive = true;
    }

    if(isActive) {
        return (
            <div style={redContainerStyle} onClick={async (e) => {
                e.stopPropagation();
                e.preventDefault();
                
                try {
                    await finishProject(project);
                    console.log("Project finished successfully");
                    window.location.reload();
                } catch (err) {
                    console.error("Error finishing project:", err);
                    alert("Error finishing project: " + err.message);
                }
            }}>
                --- Finish Project --- 
            </div>
        )
    }
}

const UnassignWorkerButton = (worker, projectName) => {
  return (
    <button
      onClick={async (e) => {
        e.stopPropagation();
        try {
          const response = await unassignWorker({
            project: projectName,
            worker: worker,
          });
          const result = response.data;
          console.log("Unassign result:", result);
          if (result === "OK") {
            window.location.reload();
          } else {
            alert("Unassign failed: " + result);
          }
        } catch (err) {
          console.error("Unassign error:", err);
          alert("Unassign error: " + err.message);
        }
      }}
      style={purpleContainerStyle}
    >
      Unassign
    </button>
  );
};

const ViewDetailsButton = ({ onClick }) => {
  return (
    <button
      onClick={onClick}
      style={viewDetailsButtonStyle}
    >
      View Details
    </button>
  );
};

const AssignWorkerDropdown = ({ project }) => {
  const [showDropdown, setShowDropdown] = useState(false);
  const [availableWorkers, setAvailableWorkers] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  
  const handleButtonClick = async (e) => {
    e.stopPropagation();
    setShowDropdown(true);
    setIsLoading(true);
    
    try {
      const workers = await getWorkers();
      
      const filteredWorkers = workers.filter(worker => {
        const isAvailable = worker.workload < 12 && !project.workers.includes(worker.name);
        const providesNeededQualifications = worker.qualifications.some(qual => 
          project.missingQualifications.includes(qual)
        );
        return isAvailable && providesNeededQualifications;
      });
      
      setAvailableWorkers(filteredWorkers);
      setIsLoading(false);
      
      if (filteredWorkers.length === 0) {
        alert("No available workers who can help with this project's missing qualifications.");
        setShowDropdown(false);
      }
    } catch (err) {
      console.error("Error loading workers:", err);
      alert("Error loading workers: " + err.message);
      setIsLoading(false);
    }
  };
  
  const handleAssign = async (workerName) => {
    try {
      const assignmentData = {
        project: project.name,
        worker: workerName
      };
      
      const response = await assignWorker(assignmentData);
      
      if (response.data === "OK") {
        window.location.reload();
      } else {
        alert("Failed to assign worker: " + response.data);
      }
    } catch (err) {
      console.error("Error in assignment:", err);
      alert("Error: " + err.message);
    }
    
    setShowDropdown(false);
  };

  const handleClickOutside = (e) => {
    setShowDropdown(false);
  };
  
  return (
    <div style={dropdownContainerStyle}>
      <div 
        style={yellowContainerStyle} 
        onClick={handleButtonClick}
      >
        {isLoading ? "Loading workers..." : "Assign Worker to Project"}
      </div>
      
      {showDropdown && (
        <div style={dropdownStyle}>
          {isLoading ? (
            <div style={{ padding: '20px', textAlign: 'center' }}>
              Loading available workers...
            </div>
          ) : (
            availableWorkers.map((worker) => {
              const matchingQuals = worker.qualifications.filter(qual => 
                project.qualifications.includes(qual)
              );
              
              const providedMissingQuals = worker.qualifications.filter(qual => 
                project.missingQualifications.includes(qual)
              );
              
              return (
                <div
                  key={worker.name}
                  style={dropdownItemStyle}
                  onMouseOver={(e) => e.currentTarget.style.backgroundColor = '#333'}
                  onMouseOut={(e) => e.currentTarget.style.backgroundColor = '#424242'}
                  onClick={(e) => {
                    e.stopPropagation();
                    handleAssign(worker.name);
                  }}
                >
                  <div>
                    <strong>{worker.name}</strong> (Workload: {worker.workload})
                  </div>
                  {matchingQuals.length > 0 && (
                    <div style={matchingQualStyle}>
                      Matching quals: {matchingQuals.join(', ')}
                    </div>
                  )}
                  {providedMissingQuals.length > 0 && (
                    <div style={missingQualStyle}>
                      Provides missing: {providedMissingQuals.join(', ')}
                    </div>
                  )}
                </div>
              );
            })
          )}
        </div>
      )}
      
      {showDropdown && (
        <div
          onClick={handleClickOutside}
          style={overlayStyle}
        />
      )}
    </div>
  );
};

const AssignWorkerButton = (project) => {
  if (project.status === "ACTIVE" || project.status === "FINISHED") {
    return null;
  }
  
  return <AssignWorkerDropdown project={project} />;
};

const ProjectBody = ({project}) => {
  const [selectedWorker, setSelectedWorker] = useState(null);
  const [workersCache, setWorkersCache] = useState({});
  
  const handleViewDetails = async (workerName) => {
    try {
      if (workersCache[workerName]) {
        setSelectedWorker(workersCache[workerName]);
      } else {
        const workerDetails = await getWorker(workerName);
        setWorkersCache({
          ...workersCache,
          [workerName]: workerDetails
        });
        setSelectedWorker(workerDetails);
      }
    } catch (err) {
      console.error("Error fetching worker details:", err);
      alert("Could not load worker details. Please try again.");
    }
  };
  
  const closeModal = () => {
    setSelectedWorker(null);
  };
  
  return (
    <div style={grayContainerStyle}>
      {StartProjectButton(project)}
      {FinishProjectButton(project)}
      {AssignWorkerButton(project)}
      Size: {project.size} <br />
      Status: {project.status} <br />
      <div>
        <h3>Workers:</h3>
        <ul style={{ listStyleType: 'disc' }}>
          {project.workers.length > 0 ? (
            project.workers.map((worker) => (
              <li key={worker} style={workerListItemStyle}>
                <span
                  style={workerNameLinkStyle}
                  onClick={() => window.location.assign(`/workers/${worker}`)}
                >
                  {worker}
                </span>
                <div style={buttonGroupStyle}>
                  {UnassignWorkerButton(worker, project.name)}
                  <ViewDetailsButton onClick={(e) => {
                    e.stopPropagation();
                    handleViewDetails(worker);
                  }} />
                </div>
              </li>
            ))
          ) : (
            <li>No workers assigned to this project.</li>
          )}
        </ul>
      </div>
      
      <div>
        Current Qualifications:{" "}
        <ClickList
          list={project.qualifications.filter(
            (q) => !project.missingQualifications.includes(q)
          )}
          styles={darkGrayContainerStyle}
          path="/qualifications"
        />
      </div>
      <div>
        Missing Qualifications:{" "}
        <ClickList
          list={project.missingQualifications}
          styles={redContainerStyle}
          path="/qualifications"
        />
      </div>
      
      {selectedWorker && (
        <WorkerDetailsModal 
          worker={selectedWorker} 
          onClose={closeModal} 
        />
      )}
    </div>
  );
};

const Projects = () => {
    const [projects, setProjects] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const loadProjects = () => {
        setLoading(true);
        getProjects()
            .then(data => {
                setProjects(data);
                setLoading(false);
            })
            .catch(err => {
                console.error('Error loading projects:', err);
                setError("Cannot load projects. Please try again later.");
                setLoading(false);
            });
    };

    useEffect(() => {
        loadProjects();
    }, []);

    const handleProjectCreated = () => {
        loadProjects();
    };

    const active = LocationID('projects', projects, 'name');

    return (
        <div style={pageStyle}>
            <h1>
                This page displays a table containing all the projects.
            </h1>
            
            <CreateProjectForm onProjectCreated={handleProjectCreated} />
            
            {loading && <p>Loading Projects</p>}
            {error && !loading && <p>{error}</p>}
            {projects.length === 0 && !loading && !error && (
                <p>There are no projects!</p>
            )}
            {!loading && !error && projects.length > 0 && (
                <ClickList active={active} list={projects} item={Project} path='/projects' id='name' />
            )}
        </div>
    );
};

export default Projects