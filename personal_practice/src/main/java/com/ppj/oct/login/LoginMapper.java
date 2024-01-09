package com.ppj.oct.login;

import java.util.List;

public interface LoginMapper {
	public abstract List<SqueezeUser> getUserById(String id);
	public abstract int registUser(SqueezeUser su);
}
