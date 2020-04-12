import ServiceBase from "./ServiceBase";

const SERVICE_FAMILY_CREATE        = 'api/familias/';
const SERVICE_FAMILY_GET           = 'api/familias/informacion/';
const SERVICE_FAMILY_CREATE_MEMBER = 'api/pacientes/';

class ServiceFamily extends ServiceBase {
	
	getFamily = (onSuccess = false, onFailure = false) => {
		this.axios.get(this.getBaseService() + SERVICE_FAMILY_GET,
			{headers: this.getHeaders()}
		)
				.then(result => {
					if (onSuccess) {
						onSuccess(result.data);
					}
				})
				.catch((error) => this.handleAxiosErrors(error, onFailure));
	};
	
	register = (family, onSuccess = false, onFailure = false) => {
		this.axios.post(this.getBaseService() + SERVICE_FAMILY_CREATE,
			{
				nombre        : family.name,
				telefono      : family.phone,
				latitud       : family.latitude,
				longitud      : family.longitude,
				direccion     : family.address,
				zona          : family.zone,
				ciudad        : family.city,
				departamentoId: family.department,
				municipioId   : family.municipality,
				centroSaludId : family.healthCenter,
				controlInicial: 'false',
			}, {
				headers: this.getHeaders()
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
	
	registerMember(member, onSuccess = false, onFailure = false) {
		this.axios.post(this.getBaseService() + SERVICE_FAMILY_CREATE_MEMBER,
			{
				id             : 0,
				nombre         : member.name,
				edad           : member.age,
				genero         : member.sex,
				gestacion      : member.gestation,
				tiempoGestacion: member.gestationTime,
				ocupacion      : member.occupation,
				
			}, {
				headers: this.getHeaders()
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
	}
	
	updateMember(member, onSuccess = false, onFailure = false) {
		this.axios.put(this.getBaseService() + SERVICE_FAMILY_CREATE_MEMBER,
			{
				id             : member.id,
				nombre         : member.name,
				edad           : member.age,
				genero         : member.sex,
				gestacion      : member.gestation,
				tiempoGestacion: member.gestationTime,
				controlInicial : 'control',
				ocupacion      : member.occupation,
			}, {
				headers: this.getHeaders()
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
	}
	
	dailyControl(userId, sicknesses, symptoms, countries, onSuccess = false) {
		this.axios.post(this.getBaseService() + 'api/pacientes/' + userId + '/control-diario/',
			{
				enfermedadesBase: sicknesses,
				sintomas        : symptoms,
				paises          : countries,
			}, {headers: this.getHeaders()})
				.then((result) => {
						if (onSuccess) {
							onSuccess(result.data);
						}
					}
				)
				.catch((error) => this.handleAxiosErrors(error));
	}
	
	getHistory(userId, onSuccess = false, onFailure = false) {
		this.axios.get(this.getBaseService() + 'api/pacientes/' + userId + '/diganosticos/',
			{headers: this.getHeaders()})
				.then((result) => {
						if (onSuccess) {
							onSuccess(result.data);
						}
					}
				)
				.catch((error) => this.handleAxiosErrors(error));
	}
}

export default new ServiceFamily();
