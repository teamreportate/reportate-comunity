import {AUTH_SET_USER} from "../actionTypes";

const initialState   = {
	user: {
		username: '',
		token   : '',
		loged   : false,
	}
};
const countryReducer = (state = initialState, action) => {
	switch (action.type) {
		case AUTH_SET_USER: {
			return {
				...state,
				user: action.data,
			};
		}
		default:
			return state;
	}
};
export default countryReducer;