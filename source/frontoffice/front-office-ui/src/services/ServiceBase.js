import axios from "axios";

// const BASE_SERVICE = 'https://reportate.mc4.com.bo:8443/reportate-api/';
const BASE_SERVICE = 'https://dev.mc4.com.bo:9443/';

class ServiceBase {
	getBaseService = () => BASE_SERVICE;
	
	constructor() {
		this.axios = axios.create({});
	}
	
	getHeaders() {
		let token   = localStorage.getItem('token');
		let headers = {};
		if (token !== undefined && token !== null && token !== "")
			headers = {Authorization: "Bearer " + token};
		return headers;
	}
	
	handleAxiosErrors = (error, callback = false) => {
		// Error 😨
		if (error.response) {
			/*
			 * The request was made and the server responded with a
			 * status code that falls out of the range of 2xx
			 */
			console.log("-----------ERROR.RESPONSE----------");
			console.log(error.response.data);
			console.log(error.response.status);
			console.log(error.response.headers);
			if (callback && error.response.status === 403) {
				callback();
			}
		} else if (error.request) {
			/*
			 * The request was made but no response was received, `error.request`
			 * is an instance of XMLHttpRequest in the browser and an instance
			 * of http.ClientRequest in Node.js
			 */
			console.log("-----------ERROR.REQUEST----------");
			console.log(error.request);
		} else {
			// Something happened in setting up the request and triggered an Error
			console.log("-----------ERROR.MESSAGE----------");
			console.log("Error", error.message);
		}
		console.log("-----------ERROR.CONFIG----------");
		console.log(error.config);
	};
}

export default ServiceBase;
