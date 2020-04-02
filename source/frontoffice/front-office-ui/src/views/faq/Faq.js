import React from 'react';
import {Button, Collapse} from 'antd';
import {CaretRightOutlined} from '@ant-design/icons';
import {useHistory} from "react-router-dom";
import {useSelector} from "react-redux";

const {Panel} = Collapse;

const text = `
  instrucciones
`;
export default () => {
	let history = useHistory();
	const data  = useSelector(store => store.faq.data);
	
	function handleClick() {
		history.push("/dashboard");
	}
	
	return (
		<div>
			<Collapse
				bordered={false}
				defaultActiveKey={['1']}
				expandIcon={({isActive}) => <CaretRightOutlined rotate={isActive ? 90 : 0}/>}
				className="site-collapse-custom-collapse"
				style={{marginBottom: 16}}
			>
				{
					data.map(faq => {
						return (
							<Panel key={faq.id} header={faq.title} className="site-collapse-custom-panel">
								<p>{faq.content}</p>
							</Panel>
						);
					})
				}
			</Collapse>,
			<Button type="default" onClick={handleClick} style={{width: '100%'}}>Volver</Button>
		</div>
	);
	
	
}