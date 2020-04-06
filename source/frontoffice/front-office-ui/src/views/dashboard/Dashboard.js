import React, {useEffect} from "react";
import {Button} from 'antd';

import FamilyList from "./FamilyList";
import {useHistory} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {appConfigSetMessage} from "../../store/appConfig/actions";
import {FaHospitalAlt, FaNotesMedical, FaPhone} from "react-icons/fa";
import Modal from "antd/es/modal";


export default () => {
	let history    = useHistory();
	const dispatch = useDispatch();
	
	
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
			<Button size={'large'} type="default" className='options' style={styles.button}>
				<FaHospitalAlt style={{height: 30, width: 30, margin: 5}}/>
				Centros de atencion
			</Button>
			<Button size={'large'} type="default" className='options' style={styles.button} onClick={goFaq}>
				<FaNotesMedical style={{height: 30, width: 30, margin: 5}}/>
				Recomendaciones
			</Button>
		</div>
		
		<div style={{display: 'flex',}}>
			<Button size={'large'} type="primary"
							className='phone'
							style={styles.phone}
							onClick={() => {
								window.open("tel:800101104");
							}}
			>
				<FaPhone/>
				<div>
					<small>Ministerio de Salud</small>
					<p style={{margin: 0}}>800-101104</p>
				</div>
			</Button>
			
			<Button size={'large'} type="primary"
							className='phone'
							style={styles.phone}
							onClick={() => {
								window.open("tel:800148139");
							}}
			>
				<FaPhone/>
				<div>
					<small>Gobernación de Santa Cruz</small>
					<p style={{margin: 0}}>800-148139</p>
				</div>
			</Button>
		</div>
	
	</div>;
}

const styles = {
	phone : {
		flex          : 1,
		margin        : 8,
		display       : 'flex',
		justifyContent: 'space-between',
		alignItems    : 'center',
		textAlign     : 'right'
	},
	button: {
		flex         : 1,
		margin       : 8,
		display      : 'flex',
		flexDirection: 'column',
		alignItems   : 'center',
		height       : 80
	}
};