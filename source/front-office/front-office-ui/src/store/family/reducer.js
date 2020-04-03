import {FAMILY_SET_FAMILY} from "../actionTypes";

const initialState   = {
	members: [
		{
			id          : 1,
			name        : 'Juan',
			notification: true,
			token       : '',
			baseSickness: [
				{
					id  : 1,
					name: 'Diabetes'
				}
			],
			symptom     : [
				{
					id   : 1,
					name : "fiebre",
					value: "34.5"
				}
			]
		}, {
			id          : 2,
			name        : 'Marco',
			notification: true,
			token       : '',
		}, {
			id          : 3,
			name        : 'Maria',
			notification: false,
		},
	]
};
const countryReducer = (state = initialState, action) => {
	switch (action.type) {
		case FAMILY_SET_FAMILY: {
			return {
				...state,
				members: action.data,
			};
		}
		
		default:
			return state;
	}
};
export default countryReducer;