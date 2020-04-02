import React from 'react';
import './countryCard.scss';
import 'flag-icon-css/css/flag-icon.min.css';
import {useDispatch, useSelector} from "react-redux";
import {countrySetSelected} from "../store/country/actions";

export default ({country}) => {
	const dispatch    = useDispatch();
	const selected    = useSelector(store => store.country.selected);
	const handleClick = () => {
		dispatch(countrySetSelected(country));
	};
	
	return <div className={'card ' + ((selected.alpha2Code === country.alpha2Code) ? 'selected' : '')}>
		<div className={'card-header'} onClick={handleClick}>
			<span className={`icon flag-icon flag-icon-squared flag-icon-${country.alpha2Code.toLowerCase()}`}></span>
			<h3 className={'label'}>
				{country.name}
			</h3>
		</div>
		<div className={'card-content'}>
			<table style={{width: '100%'}}>
				<tbody>
				<tr>
					<td>Capital</td>
					<td className={'toRight'}>
						{country.capital}
					</td>
				</tr>
				<tr>
					<td>Region</td>
					<td className={'toRight'}>
						{country.region}
					</td>
				</tr>
				<tr>
					<td>Timezone</td>
					<td className={'toRight'}>
						{country.timezones.map((timezone, index) => {
							return <span key={index}>{timezone}</span>;
						})}
					</td>
				</tr>
				<tr>
					<td>Moneda</td>
					<td className={'toRight'}>
						{country.currencies.map((currency, index) => {
							return <span key={index} className={'tag'}>{currency}</span>;
						})}
					</td>
				</tr>
				
				<tr>
					<td>Idiomas</td>
					<td className={'toRight'}>
						{country.languages.map((language, index) => {
							return <span key={index} className={'tag'}>{language}</span>;
						})}
					</td>
				</tr>
				</tbody>
			</table>
		</div>
	</div>;
}