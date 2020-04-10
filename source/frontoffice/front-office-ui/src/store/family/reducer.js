import {
	FAMILY_ADD_MEMBER,
	FAMILY_SET_DATA,
	FAMILY_SET_FIRST_CONTROL,
	FAMILY_SET_UPDATE_MEMBER,
	FAMILY_UPDATE_MEMBER
} from "../actionTypes";

const initialState  = {
	id          : "",
	name        : "",
	phone       : "",
	address     : "",
	zone        : "",
	city        : "",
	department  : {},
	municipality: {},
	members     : [],
	fetched     : false,
	toUpdate    : {}
};
const familyReducer = (state = initialState, action) => {
	switch (action.type) {
		case FAMILY_SET_DATA: {
			const newState = {
				id          : action.data.id,
				name        : action.data.nombre,
				phone       : action.data.telefono,
				address     : action.data.direccion,
				zone        : action.data.zona,
				city        : action.data.ciudad,
				department  : action.data.departamento
											? {
						id   : action.data.departamento.id,
						name : action.data.departamento.nombre,
						phone: action.data.departamento.telefono
					}
											: {},
				municipality: action.data.municipio
											? {
						id  : action.data.municipio.id,
						name: action.data.municipio.nombre
					} : {},
				members     : [],
				fetched     : true,
				toUpdate    : {}
			};
			if (action.data.pacientes)
				action.data.pacientes.forEach(paciente => {
					newState.members.push({
						id           : paciente.id,
						name         : paciente.nombre,
						age          : paciente.edad,
						sex          : paciente.genero,
						gestation    : paciente.gestacion,
						gestationTime: paciente.tiempoGestacion,
						firstControl : paciente.controlInicial
					});
				});
			return newState;
		}
		case FAMILY_ADD_MEMBER: {
			return {
				...state,
				members: [
					...state.members, {
						id           : action.data.id,
						name         : action.data.nombre,
						age          : action.data.edad,
						sex          : action.data.genero,
						gestation    : action.data.gestacion,
						gestationTime: action.data.tiempoGestacion,
						firstControl : action.data.controlInicial
					}]
			};
		}
		case FAMILY_UPDATE_MEMBER: {
			const newMembers = [];
			state.members.forEach(member => {
				console.log(member.id === action.data.id);
				if (member.id === action.data.id) {
					newMembers.push({
						id           : action.data.id,
						name         : action.data.nombre,
						age          : action.data.edad,
						sex          : action.data.genero,
						gestation    : action.data.gestacion,
						gestationTime: action.data.tiempoGestacion,
						firstControl : true
					});
				} else
					newMembers.push(member);
			});
			
			return {
				...state,
				members: newMembers
			};
		}
		case FAMILY_SET_FIRST_CONTROL: {
			const newMembers = [];
			state.members.forEach(member => {
				console.log(member.id === action.data.id);
				if (member.id === action.data.id) {
					newMembers.push({...member, firstControl: true});
				} else
					newMembers.push(member);
			});
			
			return {
				...state,
				members: newMembers
			};
		}
		case FAMILY_SET_UPDATE_MEMBER: {
			return {
				...state,
				toUpdate: action.data
			};
		}
		default:
			return state;
	}
};
export default familyReducer;