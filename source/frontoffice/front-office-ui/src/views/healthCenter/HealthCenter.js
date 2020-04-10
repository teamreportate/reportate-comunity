import React, {useEffect, useState} from 'react';
import ServiceAppConfig from "../../services/ServiceAppConfig";
import {useSelector} from "react-redux";
import {FaPhone} from "react-icons/fa";
import {Button} from "antd";
import {useHistory} from 'react-router-dom';

export default () => {
	let history = useHistory();
	
	const municipality          = useSelector(store => store.family.municipality);
	const [centers, setCenters] = useState([]);
	
	function handleClick() {
		history.push("/dashboard");
	}
	
	useEffect(() => {
		if (municipality.id && municipality.id > 0) {
			ServiceAppConfig.getHealthCenters(municipality.id,
				(result) => {
					console.log(result);
					setCenters(result);
				},
				() => {
				
				});
		}
	}, [municipality]);
	
	return (<>
			<p>Centros de salud cercanos</p>
			{
				centers.length > 0
				? (
					centers.map((center) => {
						return <div style={styles.card}>
							<div>
								<h2 style={{margin: 0}}>{center.nombre}</h2>
								<div><b>Direccion:</b> {center.direccion}</div>
								<div><b>Telefono:</b> {center.telefono}</div>
							</div>
							<Button
								type="primary"
								shape="circle"
								icon={<FaPhone/>}
								size='large'
								style={styles.button}
								onClick={() => {
									window.open("tel:" + center.telefono);
								}}/>
						</div>;
					})
				)
				: (
					<div style={styles.card}>no centers</div>
				)
			}
			<Button type="default" onClick={handleClick} style={{width: '100%', marginTop: 8}}>Volver</Button>
		</>
	);
}
const styles = {
	card  : {
		borderRadius   : 4,
		backgroundColor: '#FFFFFF',
		border         : 'solid 1px #DDDDDD',
		padding        : 16,
		display        : 'flex',
		flexDirection  : 'row',
		justifyContent : 'space-between',
		alignItems     : 'center',
		marginBottom   : 8
	},
	button: {
		display       : 'flex',
		justifyContent: 'center',
		alignItems    : 'center',
		margin        : 8
	}
};