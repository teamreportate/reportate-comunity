import ServiceBase from "./ServiceBase";

const SERVICE_APP_GET_DEPARTMENT  = 'api/departamentos/';
const SERVICE_APP_GET_BASE_DATA   = 'api/control-diario/encuesta-inicial';
const SERVICE_APP_GET_DAILY_DATA  = 'api/control-diario/encuesta-diaria';
const SERVICE_APP_GET_OCCUPATIONS = 'api/dominios/dominio-ocupaciones/';


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
	
	getBaseData    = (onSuccess = false) => {
		this.axios.get(this.getBaseService() + SERVICE_APP_GET_BASE_DATA,
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
	getSymptoms    = (onSuccess = false, onFailure = false) => {
		this.axios.get(this.getBaseService() + SERVICE_APP_GET_DAILY_DATA,
			{headers: this.getHeaders()})
				.then((result) => {
						if (onSuccess) {
							onSuccess(result.data);
						}
					}
				)
				.catch((error) => {
					this.handleAxiosErrors(error);
				});
	};
	getOccupations = (onSuccess = false, onFailure = false) => {
		this.axios.get(this.getBaseService() + SERVICE_APP_GET_OCCUPATIONS,
			{headers: this.getHeaders()})
				.then((result) => {
						if (onSuccess) {
							onSuccess(result.data);
						}
					}
				)
				.catch((error) => {
					this.handleAxiosErrors(error);
				});
	};
	
}

export default new ServiceAppConfig();