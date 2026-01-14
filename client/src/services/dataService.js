import axios from 'axios'

const SERVER_ADDRESS = 'http://localhost:4567/api/'

//Qualification
export function getQualification(description) {
    return axios.get(SERVER_ADDRESS + 'qualifications/' + description).then((res) => JSON.parse(res.request.response))
}

export function getQualifications() {
    return axios.get(SERVER_ADDRESS + 'qualifications').then((res) => JSON.parse(res.request.response).sort((a, b) => a.description.localeCompare(b.description)))
}

export function createQualification(description) {
    return axios.post(SERVER_ADDRESS + 'qualifications/' + description, { description: description })
}


//Project
export function getProjects() {
    return axios.get(SERVER_ADDRESS + 'projects').then((res) => JSON.parse(res.request.response).sort((a, b) => a.name.localeCompare(b.name)))
}

export function startProject(projectDTO) {
    return axios.put(SERVER_ADDRESS + 'start', projectDTO)
}

export function finishProject(projectDTO) {
    return axios.put(SERVER_ADDRESS + 'finish', projectDTO)
}
export function createProject(projectData) {
    return axios.post(SERVER_ADDRESS + 'projects/' + projectData.name, projectData)
}

//Worker
export function getWorker(name) {
    return axios.get(SERVER_ADDRESS + 'workers/:' + name).then((res) => JSON.parse(res.request.response))
}

export function getWorkers() {
    return axios.get(SERVER_ADDRESS + 'workers').then((res) => JSON.parse(res.request.response))
}

export function assignWorker(assignmentDTO) {
    return axios.put(SERVER_ADDRESS + "assign", assignmentDTO);
  }
  
export function unassignWorker(projectDTO) {
    return axios.put(SERVER_ADDRESS + "unassign", projectDTO);
  }
  
  export function createWorker(workerData) {
    return axios.post(SERVER_ADDRESS + 'workers/' + workerData.name, workerData)
}
  
