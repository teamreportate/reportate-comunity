import React, {useEffect} from "react";
import {Alert, Button} from 'antd';
import FamilyList from "./FamilyList";
import {useHistory} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {appConfigSetMessage} from "../../store/appConfig/actions";

export default () => {
	let history    = useHistory();
	const message  = useSelector(store => store.appConfig.message);
	const dispatch = useDispatch();
	
	
	useEffect(() => {
		//TODO validate if login redirect to dashboard, else redirect to loginpage
	}, []);
	
	const goFaq = () => {
		history.push('/faq');
	};
	
	return <div>
		{
			message
			?
			<Alert message={message.text} type={message.type} showIcon style={{marginBottom: 16}} closable afterClose={() => {
				dispatch(appConfigSetMessage(false));
			}}/>
			: null
		}
		
		
		<FamilyList/>
		<hr/>
		<div style={{display: 'flex'}}>
			<Button size={'large'} type="default" className='options' style={{flex: 1, margin: 8}}>
				Centros de atencion
			</Button>
			<Button size={'large'} type="default" className='options' style={{flex: 1, margin: 8}} onClick={goFaq}>
				Recomendaciones
			</Button>
		</div>
		
		<div style={{display: 'flex',}}>
			<Button size={'large'} type="primary" className='phone' style={{flex: 1, margin: 8}}>
				<small>Nmero de emergencia 1</small>
				<p>800 123456</p>
			</Button>
			<Button size={'large'} type="primary" className='phone' style={{flex: 1, margin: 8}}>
				<small>Nmero de emergencia 2</small>
				<p>800 123457</p>
			</Button>
		</div>
	
	</div>;
}