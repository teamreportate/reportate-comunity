import React, {useEffect, useState} from 'react';
import {useSelector} from "react-redux";
import {useHistory} from "react-router-dom";
import ServiceFamily from "../../services/ServiceFamily";
import {Button, Collapse} from "antd";
import {FaCheck} from "react-icons/fa";


export default () => {
	const member                            = useSelector(store => store.family.toUpdate);
	const [memberHistory, setMemberHistory] = useState([]);
	let history                             = useHistory();
	useEffect(() => {
		ServiceFamily.getHistory(member.id,
			(result) => {
				setMemberHistory(result);
			});
	}, [member.id]);
	
	function handleClick() {
		history.push("/dashboard");
	}
	
	return (
		<div>
			{memberHistory.map((item => {
				return <div key={item.id} style={styles.card}>
					<div style={{padding: 16}}>
						<small>{item.fechaRegistro}</small>
						<p style={{margin: 0}}>
							<span><b>Recomendación:</b></span>
							{item.recomendacion}
						</p>
					</div>
					<Collapse bordered={false}
										className="site-collapse-custom-collapse">
						<Collapse.Panel key="1"
														header="Síntomas reportados"
														className="site-collapse-custom-panel">
							{item.sintomas.map(sintoma => {
								return <p key={sintoma.id} style={styles.p}>
									<FaCheck style={{margin: 8, color: '#4C9FDC'}}/> {sintoma.nombre}
								</p>;
							})}
						</Collapse.Panel>
					</Collapse>
				</div>;
			}))}
			
			<Button type="default" onClick={handleClick} style={{width: '100%', marginBottom: 16}}>Volver</Button>
			<style>{`
      [data-theme='compact'] .site-collapse-custom-collapse .site-collapse-custom-panel,
			.site-collapse-custom-collapse .site-collapse-custom-panel {
				background: #FFFFFF;
				border-radius: 2px;
				border: 0px;
				overflow: hidden;
			}
    `}</style>
		</div>
	);
}
const styles = {
	card  : {
		borderRadius   : 4,
		backgroundColor: '#FFFFFF',
		border         : 'solid 1px #DDDDDD',
		display        : 'flex',
		flexDirection  : 'column',
		justifyContent : 'space-between',
		marginBottom   : 8
	},
	button: {
		display       : 'flex',
		justifyContent: 'center',
		alignItems    : 'center',
		margin        : 8
	},
	p     : {
		display   : 'flex',
		alignItems: 'center',
		margin    : 0,
	}
};
