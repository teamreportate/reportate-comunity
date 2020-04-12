import React, {useEffect} from 'react';
import {useSelector} from "react-redux";
import ServiceFamily from "../../services/ServiceFamily";

export default () => {
	const member = useSelector(store => store.family.toUpdate);
	
	useEffect(() => {
		ServiceFamily.getHistory(member.id,
			(result) => {
				console.log(result);
			});
	}, [member.id]);
	
	return (
		<div>History</div>
	);
}