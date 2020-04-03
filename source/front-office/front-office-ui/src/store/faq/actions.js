import {FAQ_SET_DATA} from "../actionTypes";

export const faqSetData = (user) => {
	return {
		type: FAQ_SET_DATA,
		data: user,
	};
};