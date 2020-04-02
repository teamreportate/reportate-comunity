import React, {useEffect} from 'react';
import {Redirect} from "react-router-dom";

export default () => {
	useEffect(() => {
		//TODO validate if login redirect to dashboard, else redirect to loginpage
	}, []);
	return <Redirect to="/family-data"/>;
}