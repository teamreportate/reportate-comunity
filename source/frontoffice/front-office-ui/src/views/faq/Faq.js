import React from 'react';
import {Button, Collapse} from 'antd';
import {CaretRightOutlined} from '@ant-design/icons';
import {useHistory} from "react-router-dom";

const {Panel} = Collapse;

const text = `
  instrucciones
`;
export default () => {
	let history = useHistory();
	// const data  = useSelector(store => store.faq.data);
	
	const data = [
		{
			title  : "LAVARSE LAS MANOS",
			content: "Lávese las manos regularmente con un desinfectante a base de alcohol o con agua y jabón. Tocarse la cara después de tocar superficies contaminadas o a personas enfermas es una de las formas en que se puede transmitir el virus. Al limpiar las manos se puede reducir el riesgo"
		},
		{
			title  : "LIMPIEZA DE SUPERFICIES",
			content: "Limpie regularmente con desinfectante las superficies como bancos de cocina y escritorios de trabajo ."
		},
		{
			title  : "INFORMARSE",
			content: "Infórmese sobre el COVID-19 a través de fuentes confiables: su agencia de salud pública local o nacional, el sitio web de la OMS o su profesional sanitarios local. Todos deben conocer los síntomas: para la mayoría de las personas, comienza con fiebre y tos seca, no con secreción nasal. La mayoría de las personas tendrá una enfermedad leve y mejorará sin necesidad de ningún cuidado especial."
		},
		{
			title  : "EVITAR LOS VIAJES",
			content: "Evitar viajar si tiene fiebre o tos, y en caso de enfermarse durante un vuelo, informar a la tripulación de inmediato. Una vez que llegue a casa, póngase en contacto con un profesional de la salud y cuéntele dónde ha estado."
		},
		{
			title  : "CUIDADO AL TOSER O ESTORNUDAR",
			content: "Si tose o estornuda, hágalo en la manga o use un pañuelo de papel. Deseche el pañuelo inmediatamente en un contenedor de basura cerrado y luego lávese las manos."
		},
		{
			title  : "SI ES MAYOR, EVITAR LAS ÁREAS MUY CONCURRIDAS",
			content: "Si tiene más de 60 años o si tiene una afección subyacente como una enfermedad cardiovascular, una afección respiratoria o diabetes, corre un mayor riesgo de desarrollar una enfermedad grave. Podría tomar precauciones adicionales para evitar áreas abarrotadas o lugares donde pueda interactuar con personas enfermas."
		},
		{
			title  : "QUEDARSE EN CASA SI SE ESTÁ ENFERMO",
			content: "Si no se siente bien, quédese en casa y llame a su médico o profesional de salud local. Él o ella le hará algunas preguntas sobre sus síntomas, dónde ha estado y con quién ha tenido contacto. Esto ayudará a asegurarse de que reciba el asesoramiento correcto, se dirija al centro de salud adecuado y evitará que infecte a otros."
		},
		{
			title  : "CUIDADOS EN CASA",
			content: "Si está enfermo, quédese en casa y coma y duerma por separado de su familia, use diferentes utensilios y cubiertos para comer."
		},
		{
			title  : "CONSULTAR CON EL MÉDICO",
			content: "Si tiene dificultad para respirar, llame a su médico y busque atención médica de inmediato."
		},
		{
			title  : "HABLAR CON LA COMUNIDAD Y EL TRABAJO",
			content: "Es normal y comprensible sentirse ansioso, especialmente si vive en un país o comunidad que ha sido afectada. Descubra lo que puede hacer en su comunidad. Discuta cómo mantenerse seguro con su lugar de trabajo, escuela o lugar de culto."
		},
	];
	
	function handleClick() {
		history.push("/dashboard");
	}
	
	return (
		<div>
			<Collapse
				bordered={false}
				defaultActiveKey={['0']}
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