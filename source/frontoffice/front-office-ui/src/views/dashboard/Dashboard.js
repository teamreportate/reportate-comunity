import React from "react";
import {Button} from 'antd';

import FamilyList from "./FamilyList";
import {useHistory} from "react-router-dom";
import {FaHospitalAlt, FaNotesMedical, FaPhone} from "react-icons/fa";


export default () => {
	let history = useHistory();
	
	const goFaq = () => {
		history.push('/faq');
	};
	
	return <div>
		<FamilyList/>
		<hr/>
		<div style={{display: 'flex'}}>
			<Button size={'large'} type="default" className='options'
							style={{...styles.button, marginRight: 8, marginBottom: 8}}>
				<FaHospitalAlt style={{height: 30, width: 30, margin: 5, color: '#A3BF49'}}/>
				<span style={{color: '#A3BF49'}}>Centros de atención</span>
			</Button>
			<Button size={'large'} type="default" className='options'
							style={{...styles.button, marginLeft: 8, marginBottom: 8}}
							onClick={goFaq}>
				<FaNotesMedical style={{height: 30, width: 30, margin: 5, color: '#A3BF49'}}/>
				<span style={{color: '#A3BF49'}}>Recomendaciones</span>
			</Button>
		</div>
		
		<div>
			<Button size={'large'} type="primary"
							className='phone'
							style={{...styles.phone}}
							onClick={() => {
								window.open("tel:800101104");
							}}
			>
				<FaPhone style={{margin: 5}}/>
				<div>
					<small>Ministerio de Salud</small>
					<p style={{margin: 0}}>800-101104</p>
				</div>
			</Button>
			<Button size={'large'} type="primary"
							className='phone'
							style={{...styles.phone}}
							onClick={() => {
								window.open("tel:800148139");
							}}
			>
				<FaPhone style={{margin: 5}}/>
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
		width         : '100%',
		marginTop     : 8,
		marginBottom  : 8,
		display       : 'flex',
		justifyContent: 'left',
		alignItems    : 'center',
		textAlign     : 'left'
	},
	button: {
		flex         : 1,
		display      : 'flex',
		flexDirection: 'column',
		alignItems   : 'center',
		height       : 80
	}
};