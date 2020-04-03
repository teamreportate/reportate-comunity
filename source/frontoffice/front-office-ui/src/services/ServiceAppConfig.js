import ServiceBase from "./ServiceBase";

const SERVICE_APP_GET_DEPARTMENT = 'api/departamentos/';
const SERVICE_APP_GET_BASE_DATA  = 'api/control-diario/encuesta-inicial';
const SERVICE_APP_GET_DAILY_DATA = 'api/control-diario/encuesta-diaria';

class ServiceAppConfig extends ServiceBase {
	
	getDepartments = (onSuccess = false) => {
		this.axios.get(this.getBaseService() + SERVICE_APP_GET_DEPARTMENT,
			{
				headers: this.getHeaders()
			})
				.then((result) => {
						if (onSuccess) {
							onSuccess(result.data);
						}
					}
				)
				.catch((error) => this.handleAxiosErrors(error));
	};
	
	getBaseData = (onSuccess = false) => {
		this.axios.get(this.getBaseService() + SERVICE_APP_GET_BASE_DATA,
			{})
				.then((result) => {
						if (onSuccess) {
							onSuccess(result.data);
						}
					}
				)
				.catch((error) => this.handleAxiosErrors(error));
	};
	getSymptoms = (onSuccess = false) => {
		this.axios.get(this.getBaseService() + SERVICE_APP_GET_DAILY_DATA,
			{})
				.then((result) => {
						if (onSuccess) {
							onSuccess(result.data);
						}
					}
				)
				.catch((error) => this.handleAxiosErrors(error));
	};
	
}

export default new ServiceAppConfig();