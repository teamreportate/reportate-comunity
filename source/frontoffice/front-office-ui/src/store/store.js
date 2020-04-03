import {combineReducers, createStore} from 'redux';
import authReducer from "./auth/reducer";
import familyReducer from "./family/reducer";
import faqReducer from "./faq/reducer";
import appConfigReducer from "./appConfig/reducer";

const rootReducer = combineReducers({
	appConfig: appConfigReducer,
	auth     : authReducer,
	family   : familyReducer,
	faq      : faqReducer,
});

// const store = createStore(rootReducer);
const store = createStore(
	rootReducer,
	window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);
export default store;