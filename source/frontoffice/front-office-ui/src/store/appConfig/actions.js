import {APP_CONFIG_SET_BASE_DATA, APP_CONFIG_SET_DEPARTMENT, APP_CONFIG_SET_MESSAGE} from "../actionTypes";

export const appConfigSetBaseData = (data) => {
	return {
		type: APP_CONFIG_SET_BASE_DATA,
		data: data,
	};
};

export const appConfigSetDepartment = (departments) => {
	return {
		type: APP_CONFIG_SET_DEPARTMENT,
		data: departments,
	};
};
export const appConfigSetMessage    = (message) => {
	return {
		type: APP_CONFIG_SET_MESSAGE,
		data: message,
	};
};