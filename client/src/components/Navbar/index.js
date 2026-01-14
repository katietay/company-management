import React, { useState } from 'react'
import {
  Nav,
  NavLogo,
  NavMenu,
  NavLink,
  MobileIcon,
  DrawerMenu
} from './navbarElements'

import hamburgerIcon from '../../hamburger.svg'

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false)

  return (
    <Nav>
      <NavLogo>CSU – CS415 t19</NavLogo>

      <MobileIcon
        src={hamburgerIcon}
        alt="Open Menu"
        onClick={() => setIsOpen(!isOpen)}
      />

      <NavMenu>
        <NavLink to="/">Home</NavLink>
        <NavLink to="/workers">Workers</NavLink>
        <NavLink to="/projects">Projects</NavLink>
        <NavLink to="/qualifications">Qualifications</NavLink>
        <NavLink to="/about">About</NavLink>
      </NavMenu>

      <DrawerMenu isOpen={isOpen}>
        <NavLink to="/" onClick={() => setIsOpen(false)}>Home</NavLink>
        <NavLink to="/workers" onClick={() => setIsOpen(false)}>Workers</NavLink>
        <NavLink to="/projects" onClick={() => setIsOpen(false)}>Projects</NavLink>
        <NavLink to="/qualifications" onClick={() => setIsOpen(false)}>Qualifications</NavLink>
        <NavLink to="/about" onClick={() => setIsOpen(false)}>About</NavLink>
      </DrawerMenu>
    </Nav>
  )
}

export default Navbar
