import ServiceBase from "./ServiceBase";

const SERVICE_GET_ALL                = 'all/';
const SERVICE_GET_BY_COUNTRY_CODE    = 'alpha/';
const SERVICE_GET_BY_COUNTRY_CODES   = 'alpha/';
const SERVICE_GET_BY_NAME            = 'name/';
const SERVICE_SEARCH_BY_CALLING_CODE = 'callingcode/';
const SERVICE_SEARCH_BY_CAPITAL_CITY = 'capital/';
const SERVICE_SEARCH_BY_CURRENCY     = 'currency/';
const SERVICE_SEARCH_BY_LANGUAGE     = 'lang/';
const SERVICE_SEARCH_BY_REGION       = 'region/';
const SERVICE_SEARCH_BY_SUBREGION    = 'subregion/';

class ServiceCountry extends ServiceBase {
	
	getAll       = (callback = false) => {
		this.axios.get(this.getCountryService() + SERVICE_GET_ALL)
				.then((result) => {
						if (callback) {
							callback(result.data);
						}
					}
				)
				.catch((error) => this.handleAxiosErrors(error));
	};
	getAllByName = (name, callback = false) => {
		this.axios.get(this.getCountryService() + SERVICE_GET_BY_NAME + name)
				.then((result) => {
						if (callback) {
							callback(result.data);
						}
					}
				)
				.catch((error) => this.handleAxiosErrors(error));
	};
}

export default new ServiceCountry();