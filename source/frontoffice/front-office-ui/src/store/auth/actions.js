import {AUTH_SET_USER} from "../actionTypes";

export const authSetUser = (user) => {
	return {
		type: AUTH_SET_USER,
		data: user,
	};
};