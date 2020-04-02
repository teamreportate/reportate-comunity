import {Button, Checkbox, Form, Input, InputNumber, Select} from "antd";
import React, {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";

const {Option} = Select;
export default ({newMember}) => {
	let history         = useHistory();
	const [form]        = Form.useForm();
	const [sex, setSex] = useState(null);
	
	function handleClick() {
		history.push("/dashboard");
	}
	
	const handleNextClick = () => {
		if (newMember) {
			if (sex === 'hombre')
				history.push("/external-contact");
			else if (sex === 'mujer')
				history.push("/gestation");
		} else
			history.push("/dashboard");
	};
	
	useEffect(() => {
		console.log(newMember);
	}, []);
	
	return (
		<div>
			<p>Introduce la informacion basica de tu familia</p>
			<Form
				form={form}
				layout='vertical'
			>
				<Form.Item label="Nombre">
					<Input placeholder="Introduce en nombre de tu familia"/>
				</Form.Item>
				<Form.Item label="Edad">
					<InputNumber
						style={{width: '100%'}}
						defaultValue={20}
						min={0}
						max={150}
					
					/>
				</Form.Item>
				<Form.Item label="Sexo">
					<Select
						showSearch
						placeholder="Seleccione un departamento"
						optionFilterProp="children"
						filterOption={(input, option) =>
							option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
						}
						onChange={value => {
							setSex(value);
						}}
					>
						<Option value="hombre">Hombre</Option>
						<Option value="mujer">Mujer</Option>
					</Select>
				</Form.Item>
				<Form.Item>
					<Checkbox>Activar recordatorio</Checkbox>
				</Form.Item>
				{(newMember)
				 ? null : <Form.Item>
					 <Button type="default" onClick={handleClick} style={{width: '100%', marginBottom: 8}} className='options'>Reportar
						 sintoma</Button>
				 </Form.Item>
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