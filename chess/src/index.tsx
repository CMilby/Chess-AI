import React from 'react';
import ReactDOM from 'react-dom';
import { createClient, Provider as URQLProvider } from 'urql';
import { ChessBoardPage } from './pages/ChessBoardPage';

const client = createClient({ url: 'http://localhost:8080/graphql' });

const App: React.FC = () => {
	return (
		<URQLProvider value={client}>
			<ChessBoardPage />
		</URQLProvider>
	);
};

ReactDOM.render(
	<React.StrictMode>
		<App />
	</React.StrictMode>,
	document.getElementById('root')
);
