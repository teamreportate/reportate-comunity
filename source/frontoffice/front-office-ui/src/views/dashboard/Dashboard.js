import React, {useEffect} from "react";
import {Button} from 'antd';
import FamilyList from "./FamilyList";
import {useHistory} from "react-router-dom";

export default () => {
	let history = useHistory();
	
	
	useEffect(() => {
		//TODO validate if login redirect to dashboard, else redirect to loginpage
	}, []);
	
	const goFaq = () => {
		history.push('/faq');
	};
	
	return <div>
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