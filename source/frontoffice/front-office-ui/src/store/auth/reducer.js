import {AUTH_LOGOUT, AUTH_SET_USER} from "../actionTypes";

const initialState = {
	user: {
		username: '',
		token   : '',
		logged  : false,
	}
};
const authReducer  = (state = initialState, action) => {
	switch (action.type) {
		case AUTH_SET_USER: {
			return {
				...state,
				user: action.data,
			};
		}
		case AUTH_LOGOUT: {
			localStorage.clear();
			return {
				...initialState
			};
		}
		default:
			return state;
	}
	
};
export default authReducer;