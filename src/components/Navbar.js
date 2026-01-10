import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/variables.css'
import '../styles/global.css';

const Navbar = () => {

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <a className="navbar-logo">
          Techlingo
        </a>

        <ul className="navbar-menu">
          <li className="navbar-item">
            <a className="navbar-link">Home</a>
          </li>
          <li className="navbar-item">
            <a className="navbar-link">Browse Languages</a>
          </li>
          <li className="navbar-item">
            <a className="navbar-link">Guess The Term</a>
          </li>

          
            <>
              <li className="navbar-item">
                <a>Dashboard</a>
              </li>
              <li className="navbar-item">
                <a className="navbar-link">Add Questions</a>
              </li>
              <li className="navbar-item">
                <span className="navbar-user"><i className="fas fa-user"></i>User</span>
              </li>
              <li className="navbar-item">
                <button className="navbar-button">Logout</button>
              </li>
            </>
    
            <>
              <li className="navbar-item">
                <a className="navbar-link">Admin Login</a>
              </li>
              <li className="navbar-item">
                <a className="navbar-link">Admin Register</a>
              </li>
            </>
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;

