import {AUTH_LOGOUT, AUTH_SET_USER} from "../actionTypes";

export const authSetUser = (user) => {
	return {
		type: AUTH_SET_USER,
		data: user,
	};
};
export const authLogout  = () => {
	return {
		type: AUTH_LOGOUT,
	};
};