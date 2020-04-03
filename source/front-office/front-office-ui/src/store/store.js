import {combineReducers, createStore} from 'redux';
import authReducer from "./auth/reducer";
import familyReducer from "./family/reducer";
import faqReducer from "./faq/reducer";

const rootReducer = combineReducers({
	auth  : authReducer,
	family: familyReducer,
	faq   : faqReducer,
});

const store = createStore(rootReducer);

export default store;