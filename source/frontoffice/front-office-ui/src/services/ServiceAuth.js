import ServiceBase from "./ServiceBase";

const SERVICE_AUTH_SIGNIN = 'auth/signin';

class ServiceAuth extends ServiceBase {
	
	loginEmail = (user, onSuccess = false, onFailure = false) => {
		this.axios.post(this.getBaseService() + SERVICE_AUTH_SIGNIN,
			{
				username: user.username,
				password: user.password
			})
				.then((result) => {
						if (onSuccess) {
							onSuccess(result.data);
						}
					}
				)
				.catch((error) => {
					if (error.response && error.response.status === 400 && onFailure)
						onFailure(error.response.data);
					this.handleAxiosErrors(error);
				});
	};
	
}

export default new ServiceAuth();