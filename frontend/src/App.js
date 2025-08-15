import logo from './logo.svg';
import LoginForm from './components/LoginForm';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Main from './components/Main';


function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Main />} />
        <Route path='/login' element={<LoginForm />} />
      </Routes>
    </Router>
  );
}

export default App;
