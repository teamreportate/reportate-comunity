import {FAQ_SET_DATA} from "../actionTypes";

const initialState   = {
	data: [
		{
			id     : 1,
			title  : 'Pregunta',
			content: 'Respuesta',
		}, {
			id     : 2,
			title  : 'Pregunta',
			content: 'Respuesta',
		}, {
			id     : 3,
			title  : 'Pregunta',
			content: 'Respuesta',
		},
	]
};
const faqReducer = (state = initialState, action) => {
	switch (action.type) {
		case FAQ_SET_DATA: {
			return {
				...state,
				data: action.data,
			};
		}
		
		default:
			return state;
	}
};
export default faqReducer;