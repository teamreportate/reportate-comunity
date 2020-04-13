import React, {useState} from 'react';
import {Button, DatePicker, Form, Radio, Select} from "antd";
import {useHistory} from "react-router-dom";

export default () => {
	let history                 = useHistory();
	const [display, setDisplay] = useState('list');
	const [contact, setContact] = useState(false);
	const [form]                = Form.useForm();
	
	function handleClick() {
		history.push("/add-member");
	}
	
	
	function handleEditClick() {
		history.push("/update-member");
	}
	
	const contacts = [
		{id: 1, name: "yo", date: '2020-01-15', country: 'ES'},
		{id: 2, name: "conocido", date: '2020-02-15', country: 'GE'},
		{id: 3, name: "conocido", date: '2020-03-15', country: 'MX'},
	];
	
	switch (display) {
		case "list": {
			return (
				<div style={{display: 'flex', flexDirection: 'column'}}>
					<p>¿Estuvo en contacto con alguna persona que llegó del extranjero?</p>
					<Form
						form={form}
						layout='vertical'
					>
						<Form.Item>
							<Radio.Group name="radiogroup" defaultValue={contact} onChange={event => {
								setContact(event.target.value);
							}}>
								<Radio value={false}>No</Radio>
								<Radio value={true}>Si</Radio>
							</Radio.Group>
						</Form.Item>
						{
							contact
							? contacts.map((item, index) => {
								return <>
									<Button key={index} size={'large'} type="default" className='options'
													style={{flex: 1, marginBottom: 8, width: '100%'}}
													onClick={handleEditClick}>
										<div style={{display: "flex", flexDirection: "row", justifyContent: "space-between"}}>
											<p style={{margin: 0}}>{item.name}</p>
											<span>{item.date}</span>
										</div>
									</Button>
								</>;
							})
							: null
						}
						{
							contact
							? <Button size={'large'} type="default" className='add' style={{flex: 1, marginBottom: 24, width: '100%'}}
												onClick={() => {
													setDisplay('form');
												}}>
								agregar
							</Button>
							: null
						}
					</Form>
					
					
					<div style={{display: "flex", flexDirection: "row"}}>
						<Button type="default" onClick={handleClick}
										style={{width: '100%', marginBottom: 8, marginRight: 8}}>Cancelar</Button>
						<Button type="primary" onClick={() => {
							history.push("/base-data");
						}}
										style={{width: '100%', marginBottom: 8, marginLeft: 8}}>Guardar</Button>
					</div>
				
				</div>
			);
		}
		case "form": {
			return (
				<div style={{display: 'flex', flexDirection: 'column'}}>
					<p>Informacion de contacto del extrangero</p>
					<Form
						form={form}
						layout='vertical'
					>
						<Form.Item label='Quién es tu contacto'>
							<Radio.Group name="radiogroup">
								<Radio value={false}>Conocido</Radio>
								<Radio value={true}>Yo</Radio>
							</Radio.Group>
						</Form.Item>
						<Form.Item label="¿Donde estuvo?">
							<Select
								showSearch
								placeholder="Seleccione pais"
								optionFilterProp="children"
								filterOption={(input, option) =>
									option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
								}
							>
								<Select.Option value="es">España</Select.Option>
								<Select.Option value="it">Italia</Select.Option>
								<Select.Option value="ch">China</Select.Option>
							</Select>
						</Form.Item>
						<Form.Item label="¿Que fecha?">
							<DatePicker style={{width: '100%'}}/>
						</Form.Item>
						<Form.Item>
							<div style={{display: "flex", flexDirection: "row", marginTop: 8}}>
								<Button type="default" onClick={() => {
									setDisplay('list');
								}}
												style={{width: '100%', marginBottom: 8, marginRight: 8}}>Cancelar</Button>
								<Button type="primary" onClick={() => {
									setDisplay('list');
								}}
												style={{width: '100%', marginBottom: 8, marginLeft: 8}}>Guardar</Button>
							</div>
						</Form.Item>
					
					
					</Form>
				</div>
			);
		}
		default: {
			return null;
		}
	}
	
	
}
