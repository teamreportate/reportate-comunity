import React from "react";
import {Button} from 'antd';

import FamilyList from "./FamilyList";
import {useHistory} from "react-router-dom";
import {FaHospitalAlt, FaNotesMedical, FaPhone} from "react-icons/fa";
import {useSelector} from "react-redux";


export default () => {
	let history      = useHistory();
	const department = useSelector(store => store.family.department);
	
	const goFaq           = () => {
		history.push('/faq');
	};
	const goHealthCenters = () => {
		history.push('/health-centers');
	};
	
	return <div>
		<FamilyList/>
		<hr/>
		<div style={{display: 'flex'}}>
			<Button size={'large'} type="default" className='options'
							style={{...styles.button, marginRight: 8, marginBottom: 8}}
							onClick={goHealthCenters}>
				<FaHospitalAlt style={{height: 30, width: 30, margin: 5, color: '#A3BF49'}}/>
				<small style={{color: '#A3BF49'}}>Centros de atención</small>
			</Button>
			<Button size={'large'} type="default" className='options'
							style={{...styles.button, marginLeft: 8, marginBottom: 8}}
							onClick={goFaq}>
				<FaNotesMedical style={{height: 30, width: 30, margin: 5, color: '#A3BF49'}}/>
				<small style={{color: '#A3BF49'}}>Recomendaciones</small>
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
				<FaPhone style={{margin: 10}}/>
				<div>
					<small>Ministerio de Salud</small>
					<p style={{margin: 0}}>800101104</p>
				</div>
			</Button>
			{
				department.phone
				? (
					<Button size={'large'} type="primary"
									className='phone'
									style={{...styles.phone}}
									onClick={() => {
										window.open("tel:" + department.phone);
									}}
					>
						<FaPhone style={{margin: 10}}/>
						<div>
							<small>Gobernación</small>
							<p style={{margin: 0}}>{department.phone}</p>
						</div>
					</Button>
				)
				: null
			}
		
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