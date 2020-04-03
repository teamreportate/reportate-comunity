import {AUTH_SET_USER} from "../actionTypes";

const initialState   = {
	user: {
		id   : 0,
		name : '',
		token: '',
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