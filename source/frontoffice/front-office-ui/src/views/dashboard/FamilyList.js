import React from 'react';
import {useDispatch, useSelector} from "react-redux";
import {Button} from "antd";
import {useHistory} from "react-router-dom";
import {familySetUpdateMember} from "../../store/family/actions";

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
											 style={{flex: 1, marginBottom: 8}}
											 onClick={() => {
												 handleEditClick(item);
											 }}>
					{item.name}
				</Button>;
			})}
			<Button size={'large'} type="default" className='add' style={{flex: 1, marginBottom: 16}} onClick={handleClick}>
				adicionar miembro
			</Button>
		
		</div>
	
	);
}