import {
	FAMILY_ADD_MEMBER,
	FAMILY_SET_DATA,
	FAMILY_SET_FIRST_CONTROL,
	FAMILY_SET_UPDATE_MEMBER,
	FAMILY_UPDATE_MEMBER
} from "../actionTypes";

export const familySetData         = (family) => {
	return {
		type: FAMILY_SET_DATA,
		data: family,
	};
};
export const familyAddMember       = (member) => {
	return {
		type: FAMILY_ADD_MEMBER,
		data: member,
	};
};
export const familyUpdateMember    = (member) => {
	return {
		type: FAMILY_UPDATE_MEMBER,
		data: member,
	};
};
export const familySetFirstControl = (member) => {
	return {
		type: FAMILY_SET_FIRST_CONTROL,
		data: member,
	};
};
export const familySetUpdateMember = (member) => {
	return {
		type: FAMILY_SET_UPDATE_MEMBER,
		data: member,
	};
};