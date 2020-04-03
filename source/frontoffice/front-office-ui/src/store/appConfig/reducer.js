import {APP_CONFIG_SET_BASE_DATA, APP_CONFIG_SET_DEPARTMENT, APP_CONFIG_SET_MESSAGE} from "../actionTypes";

const initialState     = {
	departments   : [],
	countries     : [],
	baseSicknesses: [],
	baseSymptoms  : [],
	message       : false
};
const appConfigReducer = (state = initialState, action) => {
	switch (action.type) {
		case APP_CONFIG_SET_DEPARTMENT: {
			return {
				...state,
				user: action.data,
			};
		}
		case APP_CONFIG_SET_BASE_DATA: {
			return {
				...state,
				countries     : action.data.countries,
				baseSicknesses: action.data.baseSicknesses,
				baseSymptoms  : action.data.baseSymptoms,
			};
		}
		case APP_CONFIG_SET_MESSAGE: {
			return {
				...state,
				message: action.data
			};
		}
		default:
			return state;
	}
};
export default appConfigReducer;