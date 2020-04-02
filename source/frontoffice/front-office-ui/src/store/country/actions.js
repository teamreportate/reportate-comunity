import {COUNTRY_SET_ALL_LIST, COUNTRY_SET_FILTERED_LIST, COUNTRY_SET_SELECTED} from '../actionTypes';

export const countrySetAllList = (countryList) => {
	return {
		type: COUNTRY_SET_ALL_LIST,
		data: countryList,
	};
};

export const countrySetSelected = (country) => {
	return {
		type: COUNTRY_SET_SELECTED,
		data: country,
	};
};

export const countrySetFilteredList = (countryFiltered) => {
	return {
		type: COUNTRY_SET_FILTERED_LIST,
		data: countryFiltered,
	};
};