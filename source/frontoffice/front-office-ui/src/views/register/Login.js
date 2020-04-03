import {Button, Form, Input, Select} from "antd";
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
			<p>Registrate</p>
			<Form
				form={form}
				layout='vertical'
			>
				<Form.Item label="Nombre de usuario">
					<Input/>
				</Form.Item>
				<Form.Item label="Contraseña">
					<Input/>
				</Form.Item>
				<Form.Item>
					<div style={{display: "flex", flexDirection: "row"}}>
						<Button type="primary" onClick={handleNextClick}
										style={{width: '100%', marginBottom: 8, marginLeft: 8}}>Registrar</Button>
					</div>
				</Form.Item>
			</Form>
		</div>
	);
}