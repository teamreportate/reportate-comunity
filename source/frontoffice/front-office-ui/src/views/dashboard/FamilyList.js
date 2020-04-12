import React from 'react';
import {useDispatch, useSelector} from "react-redux";
import {Button, Collapse} from "antd";
import {useHistory} from "react-router-dom";
import {familySetUpdateMember} from "../../store/family/actions";
import {FaExclamationTriangle, FaRegPlusSquare, FaUserCircle} from "react-icons/fa";
import 'animate.css/animate.min.css';
import {AlertFilled, ContainerFilled, EditFilled} from '@ant-design/icons';


export default () => {
	let history    = useHistory();
	const dispatch = useDispatch();
	
	function handleClick() {
		history.push("/add-member");
	}
	
	const editMember    = (member) => {
		dispatch(familySetUpdateMember(member));
		if (member.firstControl) {
			history.push("/update-member");
		} else {
			history.push("/base-data");
		}
	};
	const reportMember  = (member) => {
		dispatch(familySetUpdateMember(member));
		if (member.firstControl) {
			history.push("/daily-data");
		} else {
			history.push("/base-data");
		}
	};
	const historyMember = (member) => {
		dispatch(familySetUpdateMember(member));
		if (member.firstControl) {
			history.push("/history");
		} else {
			history.push("/base-data");
		}
	};
	
	function handleEditClick(member) {
		dispatch(familySetUpdateMember(member));
		if (member.firstControl) {
			history.push("/update-member");
		} else {
			history.push("/base-data");
		}
	}
	
	const members = useSelector(store => store.family.members);
	return (
		<div style={{display: 'flex', flexDirection: 'column'}}>
			<Collapse
				bordered={false}
				className="site-collapse-custom-collapse"
				expandIcon={({isActive}) => <FaUserCircle
					style={{transition: '0.3s', height: 20, width: 20, color: isActive ? '#4c9fdc' : '#595959'}}/>}
				accordion
			>
				{
					members.map((item, index) => {
						return (
							<Collapse.Panel key={index}
															className="site-collapse-custom-panel"
							
															header={<div
																style={{display: "flex", justifyContent: 'space-between', alignItems: "center"}}>
																{item.name}
																{
																	!item.firstControl
																	? <FaExclamationTriangle className="animated infinite rubberBand delay-1s slow"
																													 style={styles.alert}/>
																	: null
																}
															</div>
															}
							>
								<div style={{display: "flex", justifyContent: 'space-between'}}>
									<Button type="primary"
													icon={<EditFilled/>}
													onClick={() => {
														editMember(item);
													}}>Editar
									</Button>
									<Button type="primary"
													icon={<ContainerFilled/>}
													onClick={() => {
														reportMember(item);
													}}>Reportar
									</Button>
									<Button type="primary"
													icon={<AlertFilled/>}
													onClick={() => {
														historyMember(item);
													}}>Diagnósticos
									</Button>
								
								</div>
							</Collapse.Panel>
						);
						
					})
				}
			</Collapse>
			<Button size={'large'} type="default" className='add' style={styles.buttons} onClick={handleClick}>
				<FaRegPlusSquare style={{marginRight: 8}}/>Adicionar miembro
			</Button>
			<style>{`
					.ant-collapse > .ant-collapse-item > .ant-collapse-header{
							padding-left:50px
					}
					.ant-collapse-borderless {
							background-color: #f0f2f5;
							border: 0;
					}
					.ant-collapse-item.site-collapse-custom-panel{
							background:#ffffff;
					}
					.ant-collapse-item.ant-collapse-item-active.site-collapse-custom-panel{
							background:#ffffff;
					}
					[data-theme='compact'] .site-collapse-custom-collapse .site-collapse-custom-panel,
						.site-collapse-custom-collapse .site-collapse-custom-panel {
						border-radius: 2px;
						margin-bottom: 8px;
						border: 1px solid #d9d9d9;
						overflow: hidden;
					}
				`}
			</style>
		</div>
	
	);
}
const styles = {
	alert  : {color: "#4c9fdc"},
	buttons: {
		flex: 1, marginBottom: 8, display: "flex", alignItems: 'center', justifyContent: "space-between"
	}
};
