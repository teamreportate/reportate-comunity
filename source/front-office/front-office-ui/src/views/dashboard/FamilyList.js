import React from 'react';
import {useSelector} from "react-redux";
import {Button} from "antd";
import {useHistory} from "react-router-dom";

export default () => {
	let history = useHistory();
	
	function handleClick() {
		history.push("/add-member");
	}
	
	function handleEditClick() {
		history.push("/update-member");
	}
	
	const members = useSelector(store => store.family.members);
	return (
		<div style={{display: 'flex', flexDirection: 'column'}}>
			{members.map((item, index) => {
				return <Button key={index} size={'large'} type="default" className='options'
											 style={{flex: 1, margin: 8}}
											 onClick={handleEditClick}>
					{item.name}
				</Button>;
			})}
			<Button size={'large'} type="default" className='add' style={{flex: 1, margin: 8}} onClick={handleClick}>
				adicionar miembro
			</Button>
		
		</div>
	
	);
}