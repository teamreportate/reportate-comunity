import {FAMILY_SET_FAMILY} from "../actionTypes";

export const familySetMembers = (family) => {
	return {
		type: FAMILY_SET_FAMILY,
		data: family,
	};
};