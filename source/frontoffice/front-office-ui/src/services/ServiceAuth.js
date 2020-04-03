import ServiceBase from "./ServiceBase";

const SERVICE_AUTH_SIGNIN = 'auth/signin/';

class ServiceAuth extends ServiceBase {
	
	loginEmail = (user, callback = false) => {
		this.axios.post(this.getBaseService() + SERVICE_AUTH_SIGNIN,
			{
				username: user.username,
				password: user.password
			})
				.then((result) => {
						if (callback) {
							callback(result.data);
						}
					}
				)
				.catch((error) => this.handleAxiosErrors(error));
	};
	
}

export default new ServiceAuth();