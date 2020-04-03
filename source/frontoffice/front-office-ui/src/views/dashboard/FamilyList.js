import React from 'react';
import {useDispatch, useSelector} from "react-redux";
import {Button} from "antd";
import {useHistory} from "react-router-dom";
import {familySetUpdateMember} from "../../store/family/actions";
import {FaRegPlusSquare, FaUserCircle} from "react-icons/fa";

export default () => {
	let history    = useHistory();
	const dispatch = useDispatch();
	
	function handleClick() {
		history.push("/add-member");
	}
	
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
			{members.map((item, index) => {
				return <Button key={index} size={'large'} type="default" className='options'
											 style={styles.buttons}
											 onClick={() => {
												 handleEditClick(item);
											 }}>
					<FaUserCircle style={{marginRight: 8}}/> {item.name}
				</Button>;
			})}
			<Button size={'large'} type="default" className='add' style={styles.buttons} onClick={handleClick}>
				<FaRegPlusSquare style={{marginRight: 8}}/>adicionar miembro
			</Button>
		
		</div>
	
	);
}
const styles = {
	buttons: {
		flex: 1, marginBottom: 8, display: "flex", alignItems: 'center', justifyContent: "left"
	}
};