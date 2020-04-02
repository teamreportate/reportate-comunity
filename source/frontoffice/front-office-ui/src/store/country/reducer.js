import {COUNTRY_SET_ALL_LIST, COUNTRY_SET_FILTERED_LIST, COUNTRY_SET_SELECTED} from '../actionTypes';

const initialState   = {
	query      : '',
	list       : [],
	displayList: [],
	selected   : {
		alpha2Code: '',
		name      : '',
	}
};
const countryReducer = (state = initialState, action) => {
	switch (action.type) {
		case COUNTRY_SET_ALL_LIST: {
			return {
				...state,
				list       : action.data,
				displayList: [...action.data]
			};
		}
		case COUNTRY_SET_SELECTED: {
			return {
				...state,
				selected: action.data,
			};
		}
		case COUNTRY_SET_FILTERED_LIST: {
			return {
				...state,
				list       : action.data,
				displayList: [...action.data]
			};
		}
		
		default:
			return state;
	}
};
export default countryReducer;