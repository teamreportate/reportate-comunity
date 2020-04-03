import {Button, Form, InputNumber, Radio, Select} from "antd";
import React, {useState} from "react";
import {useHistory} from "react-router-dom";

const {Option} = Select;
export default () => {
	let history                     = useHistory();
	const [form]                    = Form.useForm();
	const [gestation, setGestation] = useState(false);
	
	function handleClick() {
		history.push("/dashboard");
	}
	
	const handleNextClick = () => {
		history.push("/external-contact");
	};
	
	const handleGestation = (event) => {
		setGestation(event.target.value);
	};
	return (
		<div>
			<p>¿Se encuentra en estado de gestación?</p>
			<Form
				form={form}
				layout='vertical'
			>
				<Form.Item>
					<Radio.Group name="radiogroup" defaultValue={gestation} onChange={event => {
						handleGestation(event);
					}}>
						<Radio value={false}>No</Radio>
						<Radio value={true}>Si</Radio>
					</Radio.Group>
				</Form.Item>
				{
					gestation
					? <Form.Item label="Cuantas semanas">
						<InputNumber
							style={{width: '100%'}}
							defaultValue={5}
							min={1}
							max={42}
						
						/>
					</Form.Item>
					: null
				}
				<Form.Item>
					<div style={{display: "flex", flexDirection: "row"}}>
						<Button type="default" onClick={handleClick}
										style={{width: '100%', marginBottom: 8, marginRight: 8}}>Cancelar</Button>
						<Button type="primary" onClick={handleNextClick}
										style={{width: '100%', marginBottom: 8, marginLeft: 8}}>Guardar</Button>
					</div>
				</Form.Item>
				
			</Form>
		</div>
	);
}